package Axis.Axis_Spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Axis.Axis_Spring.data.dto.ListenerDto;
import Axis.Axis_Spring.service.ListenerService;
//이 컨트롤러는 ListenerEntity에 어떤 변화가 감지되면 CustomListener 클래스의 메서드가 호출되어 로그를 남기는 기능을 수행하는 역할을 보여주기 위해 작성
@RestController
@RequestMapping("/listener")
public class ListenerController {

  private ListenerService listenerService;

  @Autowired
  public ListenerController(ListenerService listenerService) {
    this.listenerService = listenerService;
  }

  @GetMapping
  public String getListenerDto(Long id) {
  
     ListenerDto listenerDto = listenerService.getListenerDto(id);
    return listenerDto.getName()+"가 조회되었습니다.";
  }

  @GetMapping("/all")
  public  ResponseEntity<List<ListenerDto>> getListenerDtoAll(){
        return ResponseEntity.status(HttpStatus.OK).body(listenerService.getListenerDtoAll());
  }
  @PostMapping
  public void saveListenerDto(String name) {
      ListenerDto listenerDto=new ListenerDto();
      listenerDto.setName(name);
      listenerService.saveListenerDto(listenerDto);
  }

  @PutMapping
  public void updateListenerDto(Long id, String name) {
    
    ListenerDto listenerDto = listenerService.getListenerDto(id);
    listenerDto.setName(name);
    listenerService.updateListenerDto(listenerDto);
  }

  @DeleteMapping
  public void deleteListenerDto(Long id) {
    ListenerDto listenerDto = listenerService.getListenerDto(id);
    listenerService.removeListenerDto(listenerDto);
  }
}