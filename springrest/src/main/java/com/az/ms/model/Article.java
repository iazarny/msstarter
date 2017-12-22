package com.az.ms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Igor_Azarny on 11/13/2017.
 */
@Entity

@Table(name = "article")

@EntityListeners(AuditingEntityListener.class)

@JsonIgnoreProperties(value = {"modified"}, allowGetters = true)

@ApiModel

public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Id", required = false)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "Title of article", required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "Short content", required = true)
    private String content;

    @ApiModelProperty(value = "Link", required = false)
    private String link;


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @LastModifiedDate
    @ApiModelProperty(value = "Modification date", required = true)
    private Date modified = new Date();

    public Article() {
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
