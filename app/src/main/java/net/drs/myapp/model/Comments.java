package net.drs.myapp.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "UserComments")
public class Comments {
	
	
	@Id
    @Column(name="commentId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long commentId;
	
	@ManyToOne
//	@JoinColumn(name="fotographer_id")
	private Fotographer fotographer;
	
	private String comment;
	
	private String commentedBy;
	
	private Long commentedByUserId;
	
	private Date commentedAt;
	
	// parent
	private Long replytocommentId;
	
	private boolean active=true;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Date getCommentedAt() {
		return commentedAt;
	}

	public void setCommentedAt(Date commentedAt) {
		this.commentedAt = commentedAt;
	}

	public Long getReplytocommentId() {
		return replytocommentId;
	}

	public void setReplytocommentId(Long replytocommentId) {
		this.replytocommentId = replytocommentId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Fotographer getFotographer() {
		return fotographer;
	}

	public void setFotographer(Fotographer fotographer) {
		this.fotographer = fotographer;
	}

	public Long getCommentedByUserId() {
		return commentedByUserId;
	}

	public void setCommentedByUserId(Long commentedByUserId) {
		this.commentedByUserId = commentedByUserId;
	}

}
