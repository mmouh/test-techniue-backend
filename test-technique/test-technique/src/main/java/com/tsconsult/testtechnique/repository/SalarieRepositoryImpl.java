package com.tsconsult.testtechnique.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tsconsult.testtechnique.Model.Salarie;

@Repository
public class SalarieRepositoryImpl implements SalarieRepository {

	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public List<Salarie> findAll() {
	
            Session currentSession = entityManager.unwrap(Session.class);	
            Query<Salarie> theQuery =
            currentSession.createQuery("from Salarie", Salarie.class);
            List<Salarie> salaries = theQuery.getResultList();
            return salaries;
	}

	@Override
	public Salarie findById(int theId) {
	
            Session currentSession = entityManager.unwrap(Session.class);
            Salarie theSalarie =currentSession.get(Salarie.class, theId);
            return theSalarie;
	}

	@Override
	public Salarie save(Salarie theSalarie) {
		Session currentSession = entityManager.unwrap(Session.class);
		return (Salarie)currentSession.merge(theSalarie);
		
	}

	@Override
	public int deleteById(int theId) {

            Session currentSession = entityManager.unwrap(Session.class);
            Query theQuery = currentSession.createQuery("delete from Salarie where id=:salarieId");
            theQuery.setParameter("salarieId", theId);		
            return theQuery.executeUpdate();
		
	}

}
