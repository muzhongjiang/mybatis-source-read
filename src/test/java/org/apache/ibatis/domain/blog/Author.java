
package org.apache.ibatis.domain.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Author implements Serializable {

    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String bio;
    protected Section favouriteSection;

    public Author() {
        this(-1, null, null, null, null, null);
    }

    public Author(int id) {
        this(id, null, null, null, null, null);
    }

}