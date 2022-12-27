package design.boilerplate.springboot.client;

import design.boilerplate.springboot.model.dto.CpfResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "CpfApiClient", url = "https://api.cpfcnpj.com.br/5ae973d7a997af13f0aaf2bf60e65803/1")
public interface CpfApiInterface {

  @RequestLine("GET /{cpf}")
  @Headers("Content-Type: application/json")
  CpfResponse getCpfInfo(@Param("cpf") String cpf);
}