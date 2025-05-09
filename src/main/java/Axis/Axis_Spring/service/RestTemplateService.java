package Axis.Axis_Spring.service;

import org.springframework.http.ResponseEntity;

import Axis.Axis_Spring.data.dto.MemberDto;

public interface RestTemplateService {
    public String getAxis();
    public String getName1();
    public String getName2();

    public ResponseEntity<MemberDto> postDto();
    public ResponseEntity<MemberDto> addHeader();

}
