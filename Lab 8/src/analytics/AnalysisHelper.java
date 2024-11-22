/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics;

/**
 *
 * @author harshalneelkamal
 */

import data.DataStore;
import java.util.*;
import model.Comment;
import model.Post;


public class AnalysisHelper {
	//Find Average number of likes per comment.
	//TODO
	public void getAverageLikesPerComments() {
		Map<Integer, Comment> comments = DataStore.getInstance().getComments();
		int likeNumber = 0;
		int commentNumber = comments.size();
		for (Comment c : comments.values()) {
			likeNumber += c.getLikes();
		}
		System.out.println("1. Find Average number of likes per comment.");
		System.out.println("Average number of likes per comments: " + likeNumber / commentNumber);

	}
	public void getPostWithMostLikedComment() {
		Map<Integer, Comment> comments = DataStore.getInstance().getComments();
		Map<Integer, Post> posts = DataStore.getInstance().getPosts();
		Comment mostLikedComment = null;

		for (Comment comment : comments.values()) {
			if (mostLikedComment == null || comment.getLikes() > mostLikedComment.getLikes()) {
				mostLikedComment = comment;
			}
		}

		if (mostLikedComment != null) {
			Post post = posts.get(mostLikedComment.getPostId());
			System.out.println("\n\n2. Find the post with most liked comments.");
			System.out.println("Post with most liked comment: ");
			System.out.println("	Post-ID - " + post.getPostId());
			System.out.println("	Comment - " + mostLikedComment.getText());
			System.out.println("	Likes - " + mostLikedComment.getLikes());
		}
	}

	public void getPostWithMostComments() {
		Map<Integer, Post> posts = DataStore.getInstance().getPosts();
		Post mostCommentedPost = null;

		for (Post post : posts.values()) {
			if (mostCommentedPost == null || post.getComments().size() > mostCommentedPost.getComments().size()) {
				mostCommentedPost = post;
			}
		}

		if (mostCommentedPost != null) {
			System.out.println("\n\n3. Find the post with most comments.");
			System.out.println("Post with most comments: " + mostCommentedPost.getPostId());
			System.out.println("	Post-ID - " + mostCommentedPost.getPostId());
			System.out.println("	Comments Count - " + mostCommentedPost.getComments().size());
		}
	}

	public void getLeastActiveUsersByPosts() {
		Map<Integer, Post> posts = DataStore.getInstance().getPosts();
		Map<Integer, Integer> postCount = new HashMap<>();

		for (Post post : posts.values()) {
			postCount.put(post.getUserId(), postCount.getOrDefault(post.getUserId(), 0) + 1);
		}

		System.out.println("\n\n4. Top 5 inactive users based on total posts number.");
		printTopUsers(postCount, "Least active users by posts");
	}

	public void getLeastActiveUsersByComments() {
		Map<Integer, Comment> comments = DataStore.getInstance().getComments();
		Map<Integer, Integer> commentCount = new HashMap<>();

		for (Comment comment : comments.values()) {
			commentCount.put(comment.getUserId(), commentCount.getOrDefault(comment.getUserId(), 0) + 1);
		}

		System.out.println("\n\n5. Top 5 inactive users based on total comments they created.");
		printTopUsers(commentCount, "Least active users by comments");
	}

	public void getLeastMostActiveUsers() {
		Map<Integer, Integer> activityCount = new HashMap<>();
		Map<Integer, Post> posts = DataStore.getInstance().getPosts();
		Map<Integer, Comment> comments = DataStore.getInstance().getComments();

		// Count posts
		for (Post post : posts.values()) {
			activityCount.put(post.getUserId(), activityCount.getOrDefault(post.getUserId(), 0) + 1);
		}

		// Count comments and likes
		for (Comment comment : comments.values()) {
			activityCount.put(comment.getUserId(), activityCount.getOrDefault(comment.getUserId(), 0) + 1);
			activityCount.put(comment.getUserId(), activityCount.get(comment.getUserId()) + comment.getLikes());
		}

		System.out.println("\n\n6. Top 5 inactive users overall (sum of comments, posts and likes)");
		printTopUsers(activityCount, "Least active users overall");
		
		System.out.println("\n\n7. Top 5 proactive users overall (sum of comments, posts and likes)");
		printBottomUsers(activityCount, "Most active users overall");
	}

	private void printTopUsers(Map<Integer, Integer> map, String message) {
		List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		for (int i = 0; i < Math.min(5, entryList.size()); i++) {
			Map.Entry<Integer, Integer> entry = entryList.get(i);
			System.out.println(message + ": User-ID " + entry.getKey() + " -> " + entry.getValue());
		}
	}


	private void printBottomUsers(Map<Integer, Integer> map, String message) {
		List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (int i = 0; i < Math.min(5, entryList.size()); i++) {
			Map.Entry<Integer, Integer> entry = entryList.get(i);
			System.out.println(message + ": User-ID " + entry.getKey() + " -> " + entry.getValue());
		}
	}

}
