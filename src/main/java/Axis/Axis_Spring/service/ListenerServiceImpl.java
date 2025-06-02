package Axis.Axis_Spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Axis.Axis_Spring.data.dto.ListenerDto;
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
    public ListenerDto getListenerDto(Long id) {
        ListenerEntity listenerEntity= listenerRepository.findById(id).get();  // findById() 메서드는 Optional을 반환하므로, get()을 사용하여 실제 엔티티를 가져옵니다.
        ListenerDto listenerDto = ListenerDto.builder()
                                    .id(listenerEntity.getId())
                                    .name(listenerEntity.getName())
                                    .build();
        return listenerDto;
    }

    @Override
     public List<ListenerDto> getListenerDtoAll(){
        return listenerRepository.findAll().stream()
                .map(listenerEntity -> ListenerDto.builder()
                        .id(listenerEntity.getId())
                        .name(listenerEntity.getName())
                        .build())
                .toList();  // List<ListenerDto>로 변환
     }

    @Override
    public void saveListenerDto(ListenerDto listenerDto) {
        ListenerEntity listenerEntity = ListenerEntity.builder()
                                    .name(listenerDto.getName())
                                    .build();
        listenerRepository.save(listenerEntity);
    }

    @Override
    public void updateListenerDto(ListenerDto listenerDto) {
        ListenerEntity listenerEntity = listenerRepository.findById(listenerDto.getId()).get();   //listener.getId())는 ID 값을 꺼내는 부분
        listenerEntity.setName(listenerDto.getName());
        listenerRepository.save(listenerEntity);
    }

    @Override
    public void removeListenerDto(ListenerDto listenerDto) {
        ListenerEntity listenerEntity = listenerRepository.findById(listenerDto.getId()).get();   //listener.getId())는 ID 값을 꺼내는 부분
        listenerRepository.delete(listenerEntity);
    }
}