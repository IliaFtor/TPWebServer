package com.example.ametist.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "environments")
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @OneToMany(mappedBy = "environment")
    private List<Directory> directories = new ArrayList<>();

    // Default constructor
    public Environment() {
        this.createdTime = LocalDateTime.now(); // Set current time by default
        this.isPublic = false; // Default to not public
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    // Method to return a simplified view of the environment
    public SimplifiedEnvironmentView getSimplifiedView() {
        SimplifiedEnvironmentView view = new SimplifiedEnvironmentView();
        view.setId(this.id);
        view.setAuthorId(this.author.getId());
        view.setAuthorName(this.author.getName());
        view.setDirectories(this.directories);
        return view;
    }

    // Inner class to represent a simplified view
    public static class SimplifiedEnvironmentView {
        private Integer id;
        private Integer authorId;
        private String authorName;
        private List<Directory> directories;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Integer authorId) {
            this.authorId = authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public List<Directory> getDirectories() {
            return directories;
        }

        public void setDirectories(List<Directory> directories) {
            this.directories = directories;
        }
    }

    public void setRoles(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRoles'");
    }
}
