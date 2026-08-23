package sh.trishul.integration.communication.service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sh.trishul.communication.model.message.Message;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfig;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfigDto;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfigMapper;
import sh.trishul.integration.communication.service.service.IntegrationCommunicationService;
import sh.trishul.repo.jpa.repository.model.dto.PageDto;

@RestController
@RequestMapping(path = "/api/v1/integrations/communication")
public class IntegrationCommunicationController {
  private final IntegrationCommunicationService service;

  public IntegrationCommunicationController(IntegrationCommunicationService service) {
    this.service = service;
  }

  @PostMapping(value = "/{configId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Message sendMessage(@PathVariable(required = true, name = "configId") Long configId,
      @Valid @NotNull @RequestBody SendMessageRequest request) {
    return this.service.sendMessage(configId, request.getTo(), request.getBody());
  }

  /**
   * Request body for sending a message via an integration.
   */
  public static class SendMessageRequest {
    @NotBlank
    private String to;

    @NotBlank
    private String body;

    public SendMessageRequest() {}

    public SendMessageRequest(String to, String body) {
      this.to = to;
      this.body = body;
    }

    public String getTo() {
      return to;
    }

    public SendMessageRequest setTo(String to) {
      this.to = to;
      return this;
    }

    public String getBody() {
      return body;
    }

    public SendMessageRequest setBody(String body) {
      this.body = body;
      return this;
    }
  }

  @GetMapping(value = "/search", consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PageDto<IntegrationCommunicationConfigDto> search(
      @RequestParam(name = "q", required = false) String query,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "100") int size,
      @RequestParam(name = "sort", defaultValue = "id") SortedSet<String> sort,
      @RequestParam(name = "order_asc", defaultValue = "true") boolean orderAscending) {
    Page<IntegrationCommunicationConfig> configPage
        = service.search(query, sort, orderAscending, page, size);

    List<IntegrationCommunicationConfigDto> configs = configPage.stream()
        .map(config -> IntegrationCommunicationConfigMapper.INSTANCE.toDto(config)).toList();

    return new PageDto<>(configs, configPage.getTotalPages(), configPage.getTotalElements());
  }
}
