package hu.isakots.martosgym.rest.tool;

import hu.isakots.martosgym.domain.Tool;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class ToolResource {

    private final ToolService toolService;

    public ToolResource(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("tools")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Tool> getAllTools() {
        return toolService.findAll();
    }

    @GetMapping("/tools/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Tool getTool(@PathVariable Long id) throws ResourceNotFoundException {
        return toolService.getTool(id);
    }

    @PostMapping("/tools")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public Tool createTool(@Valid @RequestBody Tool tool) {
        return toolService.createTool(tool);
    }

    @PutMapping("/tools")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public Tool updateTool(@Valid @RequestBody Tool tool) {
        return toolService.updateTool(tool);
    }

    @DeleteMapping("/tools/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.noContent().build();
    }

}