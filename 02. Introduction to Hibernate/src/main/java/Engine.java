import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements Runnable{
    private static EntityManager entityManager;
    private static final Scanner scan = new Scanner(System.in);


    public Engine(EntityManager entityManager) {
        this.entityManager=entityManager;
    }

    @Override
    public void run() {
        System.out.println("Please, enter exercise number:");
        int exerciseNumber= Integer.parseInt(scan.nextLine());

        switch (exerciseNumber) {
            case 2 -> changeCasing();
            case 3 -> containsEmployee();
            case 4 -> employeesWithSalaryOver50000();
            case 5 -> employeesFromDepartment();
            case 6 -> addingANewAddressAndUpdatingEmployee();
            case 7 -> addressesWithEmployeeCount();
            case 8 -> getEmployeeWithProject();
            case 9 -> findLatest10Projects();
            case 10 -> increaseSalaries();
            case 11 -> findEmployeesByFirstName();
            case 12 -> employeesMaximumSalaries();
            case 13 -> removeTowns();

        }
    }


    //Exercise 2
    private void changeCasing() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town t "
                + "SET t.name=upper(t.name) "
                + "WHERE length(t.name)<=5");
        int affectedRows = query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    //Exercise 3
    private void containsEmployee() {
        System.out.println("Please, enter employee full name:");
        String[] fullName=scan.nextLine().split("\\s+");
        String firstName=fullName[0];
        String lastName=fullName[1];

        Long singleResult = entityManager.createQuery("SELECT COUNT(e.id) FROM Employee e "
                + "WHERE e.firstName=:f_name AND e.lastName=:l_name",Long.class)
                .setParameter("f_name",firstName)
                .setParameter("l_name",lastName)
                .getSingleResult();

        System.out.println(singleResult==0?"No":"Yes");
    }

    //Exercise 4
    private void employeesWithSalaryOver50000() {
        entityManager.createQuery("SELECT e FROM Employee e "
                + "WHERE e.salary>:salary", Employee.class)
                .setParameter("salary", new BigDecimal(50000l))
                .getResultStream()
                .forEach(e-> System.out.println(e.getFirstName()));
    }

    //Exercise 5
    private void employeesFromDepartment() {
        entityManager.createQuery("SELECT e FROM Employee e "
        +"WHERE e.department.name=:d_name "
        +"ORDER BY e.salary,e.id",Employee.class)
                .setParameter("d_name","Research and Development")
                .getResultStream()
                .forEach(e-> System.out.printf("%s %s from %s - $%.2f%n"
                        ,e.getFirstName(),e.getLastName(),e.getDepartment().getName(),e.getSalary()));
    }

    //Exercise 6
    private void addingANewAddressAndUpdatingEmployee() {
        Address address=new Address();
        address.setText("Vitoshka 15");
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        System.out.println("Please, enter last name of employee:");
        String lastName=scan.nextLine();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e "
                + "WHERE e.lastName=:l_name ", Employee.class)
                .setParameter("l_name", lastName)
                .getSingleResult();
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    //Exercise 7
    private void addressesWithEmployeeCount() {
        entityManager.createQuery("SELECT a FROM Address a "
                +"ORDER BY a.employees.size DESC",Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a-> System.out.printf("%s, %s - %d employees%n",a.getText(),a.getTown(),a.getEmployees().size()));
    }

    //Exercise 8
    private void getEmployeeWithProject() {
        System.out.println("Please, enter employee id:");
        int id= Integer.parseInt(scan.nextLine());

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e "
                + "WHERE e.id=:id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        System.out.printf("%s %s - %s%n",employee.getFirstName(),employee.getLastName(),employee.getJobTitle());
        List<Project> projects=employee.getProjects().stream().collect(Collectors.toList());
        Collections.sort(projects, Comparator.comparing(Project::getName));
                projects.forEach(p-> System.out.printf("  %s%n",p.getName()));
    }

    //Exercise 9
    private void findLatest10Projects() {
        List<Project> projects = entityManager.createQuery("SELECT p FROM Project p "
                + "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();
        Collections.sort(projects,(p1,p2)->p1.getName().compareTo(p2.getName()));
        projects.forEach(p-> System.out.printf("Project name: %s%n" +
                " \tProject Description: %s%n" +
                " \tProject Start Date: %s%n" +
                " \tProject End Date: %s%n",p.getName(),p.getDescription(),p.getStartDate(),p.getEndDate()));
    }

    //Exercise 10
    private void increaseSalaries() {
        //update salary
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE Employee e "
        +"SET e.salary=e.salary*1.12 "
        +"WHERE e.department.id IN (1,2,4,11)")
                .executeUpdate();
        entityManager.getTransaction().commit();

        //print results
        entityManager.createQuery("SELECT e FROM Employee e "
                +"WHERE e.department.name IN ('Engineering','Tool Design','Marketing','Information Services')",Employee.class)
                .getResultStream()
                .forEach(e-> System.out.printf("%s %s ($%.2f)%n"
                        ,e.getFirstName(),e.getLastName(),e.getSalary()));
    }

    //Exercise 11
    private void findEmployeesByFirstName() {
        System.out.println("Enter start string of employees first name:");
        String str=scan.nextLine()+"%";
        entityManager.createQuery("SELECT e FROM Employee e "
        +"WHERE e.firstName LIKE :start_str",Employee.class)
                .setParameter("start_str",str)
                .getResultStream()
                .forEach(e-> System.out.printf("%s %s - %s - ($%.2f)%n"
                ,e.getFirstName(),e.getLastName(),e.getJobTitle(),e.getSalary()));
    }

    //Exercise 12
    private void employeesMaximumSalaries() {
        List<Object[]> resultList = entityManager.createQuery("SELECT e.department.name,MAX(e.salary) FROM Employee e " +
                "GROUP BY e.department.name " +
                "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList();


        resultList.forEach(r-> System.out.println(r[0]+" "+r[1]));
    }

    //Exercise 13
    private void removeTowns() {
        System.out.println("Please, enter town name:");
        String townName=scan.nextLine();

        Town town = entityManager.createQuery("SELECT t FROM Town t "
                + "WHERE t.name=:t_name", Town.class)
                .setParameter("t_name", townName)
                .getSingleResult();

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address  a "
                + "WHERE a.town.name=:t_name", Address.class)
                .setParameter("t_name", townName)
                .getResultList();


        entityManager.getTransaction().begin();
        addresses.forEach(a->a.getEmployees().forEach(e->e.setAddress(null)));
        addresses.forEach(a->entityManager.remove(a));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d address in %s deleted%n",addresses.size(),townName);
    }
}
