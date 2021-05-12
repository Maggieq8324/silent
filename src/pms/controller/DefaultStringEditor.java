package pms.controller;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class DefaultStringEditor  extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			try {
				
				if (text.matches(".*'.*")) {
					//setValue(text.replace('\'', '‘'));
					text = text.replace('\'', '‘');
				} /*else {
					setValue(text);
				}*/
				if (text.matches(".*<.*")) {
					text = text.replace('<', '（');
				} 
				
				if (text.matches(".*>.*")) {
					text = text.replace('>', '）');
				} 
				
				if (text.matches(".*\".*")) {
					//text = text.replace('"', '”');
				}
				
				if (text.matches(".*\\.*")) {
					text = text.replace('\\', '、');
				}
				if (text.matches(".*?.*")) {
					text = text.replace('?', '？');
				}
				setValue(text);
				
			} catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse string: " + ex.getMessage(), ex);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultStringEditor dse = new DefaultStringEditor();
		dse.setAsText("'ddsd|s'dsds''s|s???'d'''<script>alert(\"xss\")</script> ???/");
		System.out.println(dse.getAsText());
	}

}
