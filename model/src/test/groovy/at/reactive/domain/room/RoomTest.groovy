package at.reactive.domain.room

import at.reactive.domain.chat.ChatMsg
import io.reactivex.Observable
import spock.lang.Specification

class RoomTest extends Specification {
    def "should add two chat msgs to room"() {
        given:
        Room room = Room.builder().name("any_room_name").build()
        when:
        room.addChatMsg(ChatMsg.builder().origin("any_origin").text("any_bla").build())
        room.addChatMsg(ChatMsg.builder().origin("any_origin2").text("any_bla2").build())
        then:
        room.allChatMsgs.getValues().size() == 2
    }

    def "should emmit one chat msg to room on add"() {
        given:
        Room room = Room.builder().name("any_room_name").build()
        Observable<ChatMsg> msgObservation = room.startMsgObservation()
        when:
        room.addChatMsg(ChatMsg.builder().origin("any_origin").text("any_bla").build())
        then:
        msgObservation.test().valueCount() == 1
    }
}
