package com.biz.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.biz.domain.ReplyDTO;
import com.biz.persistance.ReplyDAO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class test 
{
    ReplyDAO rDAO = new ReplyDAO();
    
    private int page = 1; 
    private int cnt = 0;
    private int total = 0;
    private String currentPage = "";
    private String checkPage = "";
    private String userID = ""; // 작성자 이름
    private String content = "";  // 작성 내용
    private String regdate = "";   // 날짜
    private int score = 0; // 영화 평점
    private Document doc = null;
    private Elements replyList = null;
    
    public void daumCrawler(String movieName, String code, int x) throws IOException
    {
       
        while(true)
        {
            doc = Jsoup.connect("").get();
            
            for(Element one : replyList)
            {
                userID  = one.select("em.link_profile").text();
                content   = one.select("p.desc_review").text();
                score   = Integer.parseInt(one.select("em.emph_grade").text());
                regdate = one.select("span.info_append").text();
                
                ReplyDTO rDTO = new ReplyDTO(movieName, content, userID, regdate, score);
                rDAO.addReply(rDTO);
                
                total += score;
                cnt++;
            }
            page++;
        }
    }
}

/*
    dUrl = "https://movie.daum.net/moviedb/grade?movieId=" + code + "&type=netizen&page=" + page;
    replyList = doc.select("div.main_detail > ul > li");
    userID  = one.select("em.link_profile").text();
    content   = one.select("p.desc_review").text();
    score   = Integer.parseInt(one.select("em.emph_grade").text());
    regdate = one.select("span.info_append").text();
    replyList.size() == 0

    nUrl = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=" + code + "&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=" + page;
    replyList = doc.select("div.score_result ul li");
    userID = movie.select("div.score_reple dl > dt > em").get(0).text();
    content = movie.select("div.score_reple > p > span").text();
    score = Integer.parseInt(movie.select("div.star_score em").text());
    regdate = movie.select("div.score_reple dl > dt > em").get(1).text();
    currentPage = doc.select("input#page").attr("value");

*/        