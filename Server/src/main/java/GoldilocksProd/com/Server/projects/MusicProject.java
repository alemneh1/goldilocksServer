package GoldilocksProd.com.Server.projects;


import GoldilocksProd.com.Server.model.ProjectModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "music_table")
public class MusicProject extends ProjectModel {

    @Column(nullable = false)
    private String musicianName;

    private String musicDescription;

    public MusicProject(Long id, String projectType, String title, int date, String musicianName, String musicDescription, String thumbnailPath, String videoPath) {
        super(id, projectType, title, date, thumbnailPath, videoPath);
        this.musicianName = musicianName;
        this.musicDescription = musicDescription;
    }



}
