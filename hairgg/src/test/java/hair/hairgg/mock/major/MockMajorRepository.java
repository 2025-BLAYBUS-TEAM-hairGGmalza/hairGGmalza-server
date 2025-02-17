package hair.hairgg.mock.major;

import hair.hairgg.designer.domain.Major;
import hair.hairgg.designer.repository.MajorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockMajorRepository implements MajorRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Major> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Major> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Major> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Major getOne(Long aLong) {
        return null;
    }

    @Override
    public Major getById(Long aLong) {
        return null;
    }

    @Override
    public Major getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Major> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Major> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Major> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Major> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Major> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Major> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Major, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Major> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Major> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Major> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Major> findAll() {
        return null;
    }

    @Override
    public List<Major> findAllById(Iterable<Long> longs) {
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
    public void delete(Major entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Major> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Major> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Major> findAll(Pageable pageable) {
        return null;
    }
}
