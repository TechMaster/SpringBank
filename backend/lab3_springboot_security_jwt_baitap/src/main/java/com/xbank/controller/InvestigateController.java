package com.xbank.controller;

import com.xbank.exception.CreateTransactionException;
import com.xbank.exception.NotFoundDataException;
import com.xbank.model.Investigate;
import com.xbank.model.dto.ApiResponse;
import com.xbank.model.dto.InvestigateRequest;
import com.xbank.service.InvestigateService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/investigate")
@RequiredArgsConstructor
public class InvestigateController {

    private final InvestigateService investigateService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BANK_OPERATOR')")
    public ResponseEntity create(@ApiParam(value = "The InvestigateRequest payload") @Valid @RequestBody InvestigateRequest investigateRequest) {
        return investigateService.createInvestigate(investigateRequest)
                .map(transaction -> ResponseEntity.ok(new ApiResponse(true, "Create investigate  successfully.")))
                .orElseThrow(() -> new CreateTransactionException("Create investigate fail!"));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BANK_OPERATOR')")
    public ResponseEntity get(@PathVariable("id") String id) {
        return investigateService.getById(id)
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElseThrow(() -> new NotFoundDataException("Data not found!"));

    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BANK_OPERATOR')")
    Page<Investigate> pagination(@PageableDefault(size = 20)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "account", direction = Sort.Direction.DESC),
                                         @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                 }) Pageable pageable) {
        return investigateService.pagination(pageable);
    }

}
