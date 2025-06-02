package Axis.Axis_Spring.data.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class CustomListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);

    @PostLoad
    public void postLoad(ListenerEntity entity) {
        Long id= entity.getId(); // 엔티티를 DTO로 변환하는 메서드 호출
        LOGGER.info(id+"번 id 엔티티가 로드되었습니다");
    }

    @PrePersist
    public void prePersist(ListenerEntity entity) {
        LOGGER.info("새로운 엔티티가 저장되기 전입니다.");
    }

    @PostPersist  
    public void postPersist(ListenerEntity entity) {
        Long id= entity.getId(); // 엔티티를 DTO로 변환하는 메서드 호출
        LOGGER.info(id+"번 id 새로운 엔티티가 저장되었습니다.");
    }

    @PreUpdate
    public void preUpdate(ListenerEntity entity) {
        LOGGER.info("업데이트가 일어나기 전입니다.");
    }

    @PostUpdate
    public void postUpdate(ListenerEntity entity) {   
        Long id= entity.getId(); // 엔티티를 DTO로 변환하는 메서드 호출
        LOGGER.info(id+"번 id 업데이트가 완료되었습니다.");
    }

    @PreRemove
    public void preRemove(ListenerEntity entity) {
        LOGGER.info("삭제가 일어나기 전입니다.");
    }

    @PostRemove
    public void postRemove(ListenerEntity entity) {    Long id= entity.getId(); // 엔티티를 DTO로 변환하는 메서드 호출
        LOGGER.info(id+"번 id 삭제가 완료되었습니다.");
    }
}
