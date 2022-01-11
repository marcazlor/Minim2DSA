package edu.upc.githubfollowers;

public class User {
    String login;
    int public_repos;
    int following;
    String avatar_url;
    public User(){};

    public User(String user, int repo, int following){
        this.login=user;
        this.public_repos=repo;
        this.following=following;

    }
    public String getLogin(){
        return this.login;
    }
    public int getPublicRepos(){
        return this.public_repos;
    }
    public int getFollowing(){
        return this.following;
    }
    public String getAvatar(){
        return this.avatar_url;
    }
}
