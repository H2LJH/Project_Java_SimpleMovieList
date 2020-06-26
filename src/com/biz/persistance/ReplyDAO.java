package com.biz.persistance;

import org.bson.Document;

import com.biz.domain.ReplyDTO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ReplyDAO 
{
	MongoClient client = MongoClients.create();
	MongoDatabase db = client.getDatabase("local");
	MongoCollection<Document> collection = db.getCollection("movie");
	
	// 댓글 1건 등록
	public void addReply(ReplyDTO rDTO)
	{
		Document doc = new Document("movieName", rDTO.getMovieName()).append
								   ("content",   rDTO.getContent()).append
								   ("writer",    rDTO.getWriter()).append
								   ("regdate",   rDTO.getRegdate()).append
								   ("score",     rDTO.getScore());
		collection.insertOne(doc);
	}
	
	// 댓글 삭제(등록하려는 영화의 댓글이 존재할 때 해당 영화 댓글만 삭제)
	public void deleteReply(String movieName)
	{
		collection.deleteMany(new Document("movieName", movieName));
	}
}
