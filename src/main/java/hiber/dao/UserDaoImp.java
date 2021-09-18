package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      Query<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   public User getUserByCar(String model, Integer series) {
      Query<Car> queryCar = sessionFactory.getCurrentSession().createQuery(
                      "from Car where model = :model and series = :series")
              .setParameter("model", model)
              .setParameter("series", series);
      Car car = queryCar.getSingleResult();

      Query<User> queryUser = sessionFactory.getCurrentSession().createQuery(
              "from User where car.id = :carId");
      queryUser.setParameter("carId", car.getId());
      return queryUser.getSingleResult();
   }
}
