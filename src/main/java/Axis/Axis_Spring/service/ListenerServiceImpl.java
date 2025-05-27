package Axis.Axis_Spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Axis.Axis_Spring.data.entity.ListenerEntity;
import Axis.Axis_Spring.data.repository.ListenerRepository;

@Service
public class ListenerServiceImpl implements ListenerService {

    private ListenerRepository listenerRepository;

    @Autowired
    public ListenerServiceImpl(ListenerRepository listenerRepository) {
        this.listenerRepository = listenerRepository;
    }

    @Override
    public ListenerEntity getEntity(Long id) {
        return listenerRepository.findById(id).get();  // findById() 메서드는 Optional을 반환하므로, get()을 사용하여 실제 엔티티를 가져옵니다.
    }

    @Override
    public void saveEntity(ListenerEntity listener) {
        listenerRepository.save(listener);
    }

    @Override
    public void updateEntity(ListenerEntity listener) {
        ListenerEntity foundListener = listenerRepository.findById(listener.getId()).get();   //listener.getId())는 ID 값을 꺼내는 부분
        foundListener.setName(listener.getName());

        listenerRepository.save(foundListener);
    }

    @Override
    public void removeEntity(ListenerEntity listener) {
        listenerRepository.delete(listener);
    }
}