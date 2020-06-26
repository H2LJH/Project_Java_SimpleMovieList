package com.biz.domain;

public class ReplyDTO 
{
	private String movieName; // 영화 제목
	private String content;   // 댓글 내용
	private String writer;    // 댓글 작성자
	private String regdate;   // 댓글 작성일자
	private double score;     // 댓글 평점 
	
	public ReplyDTO() {}
	
	public ReplyDTO(String movieName, String content, String writer, String regdate, double score) 
	{
		super();
		this.movieName = movieName;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.score = score;
	}


	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ReplyDTO [movieName=" + movieName + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", score=" + score + "]";
	}
		
	
	
}
