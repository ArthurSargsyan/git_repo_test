package Model;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import beans.Item;

public class Test {
	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		cfg.configure("resources/hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		
		Criteria c = session.createCriteria(Item.class);

		Criterion cr = Restrictions.eq("itemName", "Item 3");
		c.add(cr);
		Item item = (Item) c.uniqueResult();
		System.out.println(item.getItemName());
	}
}
