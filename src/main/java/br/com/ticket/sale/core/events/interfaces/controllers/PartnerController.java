package br.com.ticket.sale.core.events.interfaces.controllers;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import br.com.ticket.sale.core.events.application.commands.partner.CreatePartnerCommand;
import br.com.ticket.sale.core.events.application.commands.partner.UpdatePartnerCommand;
import br.com.ticket.sale.core.events.application.queries.partner.ListPartnersQuery;
import br.com.ticket.sale.core.events.application.services.PartnerService;
import br.com.ticket.sale.core.events.domain.entities.partner.Partner;
import br.com.ticket.sale.core.events.domain.entities.partner.PartnerId;
import br.com.ticket.sale.core.events.interfaces.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public ApiResponse<List<Partner>> list() {
        return ApiResponse.success(partnerService.list(new ListPartnersQuery()));
    }

    @PostMapping
    public ApiResponse<Partner> create(@RequestBody Map<String, String> body) {
        CreatePartnerCommand command =
                new CreatePartnerCommand(
                        new Name(body.get("name"))
                );

        return ApiResponse.success(partnerService.create(command));
    }

    @PutMapping("/{partnerId}")
    public ApiResponse<Partner> update(@PathVariable String partnerId, @RequestBody Map<String, String> body) {
        UpdatePartnerCommand command =
                new UpdatePartnerCommand(
                        body.get("name") != null ? new Name(body.get("name")) : null
                );

        return ApiResponse.success(partnerService.update(new PartnerId(partnerId), command));
    }
}
