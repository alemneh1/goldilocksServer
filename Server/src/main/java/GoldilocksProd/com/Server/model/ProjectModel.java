package GoldilocksProd.com.Server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectType;

    @Column(nullable = false)
    private String title;

    private int date;

    private String thumbnailPath;

    private String videoPath;

    // Constructors, getters, setters, and other methods

}
