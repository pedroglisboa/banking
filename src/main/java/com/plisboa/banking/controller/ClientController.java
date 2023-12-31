package com.plisboa.banking.controller;


import com.plisboa.banking.domain.entity.Client;
import com.plisboa.banking.request.ClientRequest;
import com.plisboa.banking.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client Controller", description = "Controlador para operações nos dados de clientes")
public class ClientController {

  private final ClientService clientService;

  public ClientController
      (ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  @Operation(summary = "Pegar todos os Clientes", description = "Este endpoint retorna todos os clientes.")
  public Page<Client> getAllClients(@RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return clientService.getAllClients(pageRequest);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Pegar Cliente por id", description = "Este endpoint retorna um cliente pelo id.")
  public ResponseEntity<Client> getClientById(@PathVariable String id) {
    return clientService.getClientById(id);
  }

  @PostMapping
  @Operation(summary = "Criar Cliente", description = "Este endpoint cria um cliente.")
  public Client createClient(@Valid @RequestBody ClientRequest client) {
    return clientService.createClient(client);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Alterar Cliente", description = "Este endpoint edita um cliente filtrado pelo AccountId.")
  public ResponseEntity<Client> updateClient(@PathVariable String id,
      @Valid @RequestBody ClientRequest client) {
    return clientService.updateClient(id, client);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletar Cliente", description = "Este endpoint remnove um cliente filtrado pelo AccountId.")
  public ResponseEntity<Void> deleteClient(@PathVariable String id) {
    return clientService.deleteClient(id);
  }

}

