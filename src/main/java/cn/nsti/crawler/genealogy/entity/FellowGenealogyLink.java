package cn.nsti.crawler.genealogy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fellow_genealogy_link")
public class FellowGenealogyLink {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String org;
    private String link;

    public FellowGenealogyLink() {
    }

    public FellowGenealogyLink(String name, String org, String link) {
        this.name = name;
        this.org = org;
        this.link = link;
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

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "FellowGenealogyLink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", org='" + org + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
