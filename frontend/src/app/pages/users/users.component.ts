import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Sort } from '@angular/material/sort';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { forkJoin, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmDeleteComponent } from 'src/app/components/dialog-confirm-delete/dialog-confirm-delete.component';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit, OnDestroy {
  users: User[] = [];

  displayedColumns: string[] = ['fullName', 'email', 'action'];

  itemsPerPage: number = 10;
  totalItems: number = 0;
  first: number = 0;

  searchUserInput: string = '';
  searchUserInput$ = new Subject<string>();

  isUserChangePage: boolean = true;

  @ViewChild('paginator') paginator: MatPaginator;

  constructor(
    private titleService: Title,
    private userService: UserService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Quản lý User');

    // Get & count users
    forkJoin([
      this.userService
        .getUsers({
          max: this.itemsPerPage,
        })
        .pipe(tap((result) => (this.users = result.body))),
      this.userService
        .countUsers()
        .pipe(tap((result) => (this.totalItems = result))),
    ]).subscribe();

    // Listen search user input change
    this.searchUserInput$
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        // Call API to update data
        this.userService
          .getUsers({
            max: this.itemsPerPage,
            search: value,
          })
          .subscribe((result) => {
            this.users = result.body;
          });
      });
  }

  changePage(event: PageEvent) {
    if (this.isUserChangePage) {
      this.itemsPerPage = event.pageSize;

      this.userService
        .getUsers({
          first: event.pageIndex * event.pageSize,
          max: this.itemsPerPage,
          search: this.searchUserInput,
        })
        .subscribe((result) => {
          this.users = result.body;
        });
    }
  }

  searchUser() {
    this.searchUserInput$.next(this.searchUserInput);
  }

  openDialogDeleteUser(userId: string) {
    const dialogRef = this.dialog.open(DialogConfirmDeleteComponent, {
      width: '300px',
      data: { name: 'User' },
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
