import { Component, OnInit, ViewChild, OnDestroy } from "@angular/core";
import { MatPaginator, PageEvent } from "@angular/material/paginator";
import { Sort } from "@angular/material/sort";
import { User } from "src/app/models/user.model";
import { UserService } from "src/app/services/user.service";
import { Subject } from "rxjs";
import { debounceTime, distinctUntilChanged } from "rxjs/operators";
import { MatDialog } from "@angular/material/dialog";
import { DialogConfirmDeleteComponent } from "src/app/components/dialog-confirm-delete/dialog-confirm-delete.component";

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.css"],
})
export class UsersComponent implements OnInit, OnDestroy {
  users: User[] = [];

  displayedColumns: string[] = [
    "avatar",
    "name",
    "email",
    "birthday",
    "action",
  ];

  itemsPerPage: number = 10;
  totalItems: number = 0;
  column: string = "id";
  direction: string = "desc";

  searchUserInput: string = "";
  searchUserInput$ = new Subject<string>();

  isUserChangePage: boolean = true;

  @ViewChild("paginator") paginator: MatPaginator;

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit() {
    // Get users with default options
    this.userService
      .getUsers({
        column: this.column,
        direction: this.direction,
        itemsPerPage: this.itemsPerPage,
      })
      .subscribe((result) => {
        this.users = result.body;
        this.totalItems = parseInt(result.headers.get("X-Total-Count"));
      });

    // Listen search user input change
    this.searchUserInput$
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        // Move to first page and mark it is not manual change page to prevent duplicate call api
        this.isUserChangePage = false;
        this.paginator.firstPage();
        this.isUserChangePage = true;

        // Call API to update data
        this.userService
          .getUsers({
            column: this.column,
            direction: this.direction,
            itemsPerPage: this.itemsPerPage,
            keyword: value,
          })
          .subscribe((result) => {
            this.users = result.body;
            this.totalItems = parseInt(result.headers.get("X-Total-Count"));
          });
      });
  }

  changePage(event: PageEvent) {
    if (this.isUserChangePage) {
      this.itemsPerPage = event.pageSize;

      this.userService
        .getUsers({
          currentPage: event.pageIndex + 1,
          column: this.column,
          direction: this.direction,
          itemsPerPage: this.itemsPerPage,
          keyword: this.searchUserInput,
        })
        .subscribe((result) => {
          this.users = result.body;
          this.totalItems = parseInt(result.headers.get("X-Total-Count"));
        });
    }
  }

  sortData(sort: Sort) {
    this.column = sort.active;
    this.direction = sort.direction;

    // Move to first page and mark it is not manual change page to prevent duplicate call api
    this.isUserChangePage = false;
    this.paginator.firstPage();
    this.isUserChangePage = true;

    // Call API to update data
    this.userService
      .getUsers({
        column: this.column,
        direction: this.direction,
        itemsPerPage: this.itemsPerPage,
        keyword: this.searchUserInput,
      })
      .subscribe((result) => {
        this.users = result.body;
        this.totalItems = parseInt(result.headers.get("X-Total-Count"));
      });
  }

  searchUser() {
    this.searchUserInput$.next(this.searchUserInput);
  }

  openDialogDeleteUser(userId: string) {
    const dialogRef = this.dialog.open(DialogConfirmDeleteComponent, {
      width: "300px",
      data: { name: "User" },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.userService.deleteUser(userId).subscribe(() => {
          this.users = this.users.filter((user: User) => user.id !== userId);
        });
      }
    });
  }

  ngOnDestroy() {
    this.searchUserInput$.unsubscribe();
  }
}
