package Axis.Axis_Spring.service;

import Axis.Axis_Spring.data.dto.ListenerDto;
import Axis.Axis_Spring.data.entity.ListenerEntity;

public interface ListenerService {

  ListenerDto getListenerDto(Long id);

  void saveListenerDto(ListenerDto listener);

  void updateEntity(ListenerEntity listener);

  void removeEntity(ListenerEntity listener);
}
