package io.trishul.model.base.pojo;

import io.trishul.model.json.JsonMapper;
import io.trishul.model.reflection.ReflectionManipulator;
import java.util.Set;

public abstract class BaseModel {
    private static final ReflectionManipulator util = ReflectionManipulator.INSTANCE;
    private static final JsonMapper jsonMapper = JsonMapper.INSTANCE;

    public BaseModel() {}

    // protected BaseModel(ReflectionManipulator util,JsonMapper jsonMapper) {
    // // Note: This prevents the tests from running in parallel (project-wide).
    // // This constructor is used in unit tests to inject mock values. However,
    // // since these are static values, that means the mocks are injected at the
    // // class level. If tests are being run in parallel, a call to this
    // constructor
    // // will replace these instances with mocks and cause the other tests to fail.
    // // We currently don't run tests in parallel so it's fine.
    // // Recommendation: BaseModel being an abstract should not have unit-tests in
    // the
    // // first place. The logic should be tested by the subclasses themselves. But
    // I
    // // am leaving them now because they tests some common edge cases.
    // BaseModel.util=util;
    // BaseModel.jsonMapper=jsonMapper;
    // }

    public void outerJoin(Object other) {
        util.copy(this, other, pd -> pd.getReadMethod().invoke(other) != null);
    }

    public void outerJoin(Object other, Set<String> include) {
        util.copy(
                this,
                other,
                pd -> include.contains(pd.getName()) && pd.getReadMethod().invoke(other) != null);
    }

    public void copyToNullFields(Object existingEntity) {
        util.copy(this, existingEntity, pd -> pd.getReadMethod().invoke(this) == null);
    }

    public void override(Object other) {
        util.copy(this, other, pd -> true);
    }

    public void override(Object other, Set<String> include) {
        util.copy(this, other, pd -> include.contains(pd.getName()));
    }

    public <T extends BaseModel> T deepClone() {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) this.getClass();

        String json = this.toString();

        return fromString(json, clazz);
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }

    @Override
    public int hashCode() {
        return util.hashCode(this);
    }

    @Override
    public String toString() {
        return jsonMapper.writeString(this);
    }

    public static <T extends BaseModel> T fromString(String str, Class<T> clazz) {
        return jsonMapper.readString(str, clazz);
    }
}
