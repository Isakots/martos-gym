package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Tool;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ToolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolService.class.getName());

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public Tool getTool(Long id) throws ResourceNotFoundException {
        return toolRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format("Tool not found with id: {}", id))
        );
    }

    public Tool createTool(Tool tool) {
        LOGGER.debug("REST request to update Tool : {}", tool);
        if (tool.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have an id.");
        }
        tool.setReachable(true);
        return toolRepository.save(tool);
    }

    public Tool updateTool(Tool tool) {
        LOGGER.debug("REST request to update Tool : {}", tool);
        if (tool.getId() == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }
        return toolRepository.save(tool);
    }

    public void deleteTool(Long id) {
        LOGGER.debug("REST request to delete Tool : {}", id);
        toolRepository.deleteById(id);
    }
}
