package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import model.Company;
import model.QCompany;
import model.QComputer;

@Repository
public class CompanyDAO {
  private Logger logger;
  private HikariDataSource dataSource;
  private LocalSessionFactoryBean sessionFactory;

  /**
   * CompanyFactory contient les méthodes spécifiques de la table company.
   * @param dataSource la dataSource
   * @param sessionFactory la session
   * @throws SQLException SQLException
   */
  public CompanyDAO(HikariDataSource dataSource, LocalSessionFactoryBean sessionFactory) throws SQLException {
    this.logger = LoggerFactory.getLogger(CompanyDAO.class);
    this.dataSource = dataSource;
    this.sessionFactory = sessionFactory;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int count(String search) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany company = QCompany.company;
    int count = 0;
    if (search == null || "".equals(search)) {
      count = (int) queryFactory.select(company.id.count()).from(company).fetchCount();
    } else {
      count = (int) queryFactory.select(company.id.count()).from(company).where(company.name.like(new StringBuilder("%").append(search).append("%").toString())).fetchCount();
    }
    session.close();
    return count;
  }

  /**
   * Liste les companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> list(int nombre, int offset, String search, String sort) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany company = QCompany.company;
    List<Company> companies = new ArrayList<>();
    if (search == null || "".equals(search)) {
      if ("desc".equals(sort)) {
        companies = queryFactory.selectFrom(company).orderBy(company.name.desc()).limit(nombre).offset(offset).fetch();
      } else {
        companies = queryFactory.selectFrom(company).orderBy(company.name.asc()).limit(nombre).offset(offset).fetch();
      }
    } else {
      if ("desc".equals(sort)) {
        companies = queryFactory.selectFrom(company).where(company.name.like(new StringBuilder("%").append(search).append("%").toString())).orderBy(company.name.desc()).limit(nombre).offset(offset).fetch();
      } else {
        companies = queryFactory.selectFrom(company).where(company.name.like(new StringBuilder("%").append(search).append("%").toString())).orderBy(company.name.asc()).limit(nombre).offset(offset).fetch();
      }
    }
    session.close();
    return companies;
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> listAll() throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany company = QCompany.company;
    List<Company> companies = queryFactory.selectFrom(company).fetch();
    session.close();
    return companies;
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param company la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> showDetails(Integer id) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany qCompany = QCompany.company;
    List<Company> companies = new ArrayList<>();
    if (id != null) {
      companies = queryFactory.selectFrom(qCompany).where(qCompany.id.eq(id)).fetch();
    }
    session.close();
    return companies;
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param company la compagnie à ajouter
   * @throws SQLException SQLException
   */
  public void create(Company company) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    session.save(company);
    session.close();
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param company la compagnie à mettre à jour
   * @throws SQLException SQLException
   */
  public void update(Company company) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany qCompany = QCompany.company;
    queryFactory.update(qCompany).where(qCompany.id.eq(company.getId())).set(qCompany.name, company.getName()).execute();
    session.close();
  }

  /**
   * Supprime une companie de la table company.
   * @param company la compagnie à supprimer
   * @throws SQLException SQLException
   */
  @Transactional
  public void delete(Company company) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QCompany qCompany = QCompany.company;
    QComputer qComputer = QComputer.computer;
    queryFactory.delete(qComputer).where(qComputer.company.id.eq(company.getId())).execute();
    queryFactory.delete(qCompany).where(qCompany.id.eq(company.getId())).execute();
    session.close();
  }
}
