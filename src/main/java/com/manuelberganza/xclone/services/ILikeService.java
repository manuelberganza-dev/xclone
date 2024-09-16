package com.manuelberganza.xclone.services;

import com.manuelberganza.xclone.models.Post;

public interface ILikeService {
    boolean toggleLikeForPost(Integer id, String username);
    boolean isPostLikedByUser(Post post, String username);
}
