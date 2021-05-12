package com.we.Resp;
/**
 * 语音消息
 * 
 * @author Engineer.Jsp
 * @date 2014.10.08*
 */
public class VoiceMessage extends BaseMessage {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
