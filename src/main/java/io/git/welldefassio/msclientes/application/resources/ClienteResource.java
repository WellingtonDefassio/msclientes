package io.git.welldefassio.msclientes.application.resources;

import io.git.welldefassio.msclientes.application.representation.ClienteSaveRequest;
import io.git.welldefassio.msclientes.application.service.ClienteService;
import io.git.welldefassio.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService clienteService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente cliente = request.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf()).toUri();

        return ResponseEntity.created(headerLocation).build();
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<Cliente> dadosCliente(@RequestParam("cpf") String cpf) {
        Optional<Cliente> optionalCliente = clienteService.getByCPF(cpf);
        if(optionalCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalCliente.get());
    }
}
