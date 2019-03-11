package entity;

import global.DbTables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = DbTables.student_info)
public class Student implements Serializable {

    @Id
    @Column(name = "add_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String gender;
   @OneToMany(fetch = FetchType.LAZY,mappedBy = "student",orphanRemoval=true)
    private Set<Address> address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }



    @Override
    public String toString() {
        address.parallelStream().forEach(System.out::println);
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
