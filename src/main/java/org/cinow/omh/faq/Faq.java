package org.cinow.omh.faq;

public class Faq {
	
	private int id;
	private String question_en;
	private String question_es;
	private String answer_en;
	private String answer_es;
	private int sort_order;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion_en() {
		return question_en;
	}
	public void setQuestion_en(String question_en) {
		this.question_en = question_en;
	}
	public String getQuestion_es() {
		return question_es;
	}
	public void setQuestion_es(String question_es) {
		this.question_es = question_es;
	}
	public String getAnswer_en() {
		return answer_en;
	}
	public void setAnswer_en(String answer_en) {
		this.answer_en = answer_en;
	}
	public String getAnswer_es() {
		return answer_es;
	}
	public void setAnswer_es(String answer_es) {
		this.answer_es = answer_es;
	}
	public int getSort_order() {
		return sort_order;
	}
	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	
}
