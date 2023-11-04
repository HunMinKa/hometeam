package spring.hometeam.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import spring.hometeam.domain.entity.TeamMembership;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class TeamMembershipRepository implements JpaRepository<TeamMembership, Long> {
    @Override
    public List<TeamMembership> findAll() {
        return null;
    }

    @Override
    public List<TeamMembership> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TeamMembership> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TeamMembership> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TeamMembership entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TeamMembership> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TeamMembership> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TeamMembership> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TeamMembership> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TeamMembership> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TeamMembership> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TeamMembership> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TeamMembership getOne(Long aLong) {
        return null;
    }

    @Override
    public TeamMembership getById(Long aLong) {
        return null;
    }

    @Override
    public TeamMembership getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends TeamMembership> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TeamMembership> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TeamMembership> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TeamMembership> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TeamMembership> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TeamMembership> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TeamMembership, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}