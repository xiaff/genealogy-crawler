package cn.nsti.crawler.genealogy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qualified_ieee_name")
public class FellowName {
    @Id
    private Long id;
    private String name;

    public FellowName() {
    }

    public FellowName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FellowName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
