package hair.hairgg.mock.designer;

import hair.hairgg.designer.domain.Designer;
import hair.hairgg.designer.repository.DesignerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockDesignerRepository implements DesignerRepository {
    @Override
    public Page<Designer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Designer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Designer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Designer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Designer getOne(Long aLong) {
        return null;
    }

    @Override
    public Designer getById(Long aLong) {
        return null;
    }

    @Override
    public Designer getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Designer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Designer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Designer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Designer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Designer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Designer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Designer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Designer> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Designer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Designer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Designer> findAll() {
        return null;
    }

    @Override
    public List<Designer> findAllById(Iterable<Long> longs) {
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
    public void delete(Designer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Designer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Designer> findAll(Sort sort) {
        return null;
    }
}
