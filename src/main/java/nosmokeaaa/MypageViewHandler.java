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
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCheckIned_then_CREATE_1 (@Payload CheckIned checkIned) {
        try {
            if (checkIned.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setUserId(checkIned.getId());
                mypage.setSmokingAreaId(checkIned.getAreaId());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaid_then_CREATE_2 (@Payload Paid paid) {
        try {
            if (paid.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setDeductId(paid.getId());
                mypage.setPoint(paid.getPoint());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenEarned_then_UPDATE_1(@Payload Earned earned) {
        try {
            if (earned.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByUserId(earned.getUserId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setEarnId(earned.getId());
                    mypage.setPoint(earned.getPoint());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}