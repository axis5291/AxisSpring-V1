package Axis.Axis_Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Axis.Axis_Spring.data.entity.ListenerEntity;
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
  public String getListener(Long id) {
    ListenerEntity listenerEnity=listenerService.getEntity(id);
    return listenerEnity.getName();
  }

  @PostMapping
  public void saveListener(String name) {
    //이 컨트롤러에서 엔티티를 직접접근하여 값을 설정, 보통은 서비스계층에서 dto 객체를 사용하여 값을 설정하는 것이 일반적, dto를 만들기 고려
    listener.setId(id);
    ListenerEntity listener = new ListenerEntity();
    listener.setName(name);
    listenerService.saveEntity(listener);
  }

  @PutMapping
  public void updateListener(Long id, String name) {
    
    ListenerEntity listener = new ListenerEntity();   
    listener.setName(name);
    listenerService.updateEntity(listener);
  }

  @DeleteMapping
  public void deleteListener(Long id) {
    ListenerEntity listener = listenerService.getEntity(id);
    listenerService.removeEntity(listener);
  }
}