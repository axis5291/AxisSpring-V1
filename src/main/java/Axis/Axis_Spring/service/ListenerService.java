package Axis.Axis_Spring.service;

import Axis.Axis_Spring.data.entity.ListenerEntity;

public interface ListenerService {

  ListenerEntity getEntity(Long id);

  void saveEntity(ListenerEntity listener);

  void updateEntity(ListenerEntity listener);

  void removeEntity(ListenerEntity listener);
}
