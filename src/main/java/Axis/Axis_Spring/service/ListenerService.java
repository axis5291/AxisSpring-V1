package Axis.Axis_Spring.service;

import java.util.List;

import Axis.Axis_Spring.data.dto.ListenerDto;

public interface ListenerService {

  ListenerDto getListenerDto(Long id);

  List<ListenerDto> getListenerDtoAll();

  void saveListenerDto(ListenerDto listenerDto);

  void updateListenerDto(ListenerDto listenerDto);

  void removeListenerDto(ListenerDto listenerDto);
}
