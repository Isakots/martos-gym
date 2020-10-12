package hu.isakots.martosgym.service;


import hu.isakots.martosgym.domain.Tool;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ToolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ToolServiceTest {

    private static final String TOOL_ID = UUID.randomUUID().toString();

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolService toolService;

    @Test
    public void findAll() {
        toolService.findAll();
        verify(toolRepository).findAll();
    }

    @Test
    public void getTool_whenFound() throws ResourceNotFoundException {
        when(toolRepository.findById(any())).thenReturn(Optional.of(new Tool()));
        toolService.getTool(TOOL_ID);
        verify(toolRepository).findById(any());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getTool_whenNotFound() throws ResourceNotFoundException {
        when(toolRepository.findById(any())).thenReturn(Optional.empty());
        toolService.getTool(TOOL_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTool_whenHasId_IllegalArgumentExceptionIsThrown() {
        Tool tool = new Tool();
        tool.setId(TOOL_ID);
        toolService.createTool(tool);
    }

    @Test
    public void createTool_whenDoesNotHaveId_thenSaved() {
        ArgumentCaptor<Tool> toolArgumentCaptor = ArgumentCaptor.forClass(Tool.class);

        toolService.createTool(new Tool());

        verify(toolRepository).save(toolArgumentCaptor.capture());
        assertTrue(toolArgumentCaptor.getValue().isReachable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTool_whenDoesNotHaveId_IllegalArgumentExceptionIsThrown() {
        toolService.updateTool(new Tool());
    }

    @Test
    public void updateTool_whenHasId_thenSaved() {
        Tool tool = new Tool();
        tool.setId(TOOL_ID);
        toolService.updateTool(tool);

        verify(toolRepository).save(eq(tool));
    }

    @Test
    public void deleteTool() {
        toolService.deleteTool(TOOL_ID);
        verify(toolRepository).deleteById(eq(TOOL_ID));
    }

    @Test
    public void findByName() {
        String name = "toolName";
        toolService.findByName(name);
        verify(toolRepository).findByName(eq(name));
    }
}