package nosmokeaaa;

import nosmokeaaa.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AreaListViewHandler {


    @Autowired
    private AreaListRepository areaListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOccupied_then_CREATE_1 (@Payload Occupied occupied) {
        try {
            if (occupied.isMe()) {
                // view 객체 생성
                AreaList areaList = new AreaList();
                // view 객체에 이벤트의 Value 를 set 함
                areaList.setId(occupied.getAreaId());
                areaList.setStatus(occupied.getStatus());
                // view 레파지 토리에 save
                areaListRepository.save(areaList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenEmptied_then_UPDATE_1(@Payload Emptied emptied) {
        try {
            if (emptied.isMe())
            {
                // view 객체 조회
                Optional<AreaList> areaListOptional = areaListRepository.findById(emptied.getId());
                if( areaListOptional.isPresent())
                {
                    AreaList areaList = areaListOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    areaList.setStatus(emptied.getStatus());
                    // view 레파지 토리에 save
                    areaListRepository.save(areaList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}