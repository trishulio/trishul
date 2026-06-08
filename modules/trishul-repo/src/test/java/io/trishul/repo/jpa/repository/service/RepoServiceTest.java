package io.trishul.repo.jpa.repository.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

class RepoServiceTest {

  @Test
  void testPageRequest_returnsUnsorted_whenSortIsNull() {
    PageRequest pr = RepoService.pageRequest(null, true, 1, 10);
    assertNotNull(pr);
    assertEquals(1, pr.getPageNumber());
    assertEquals(10, pr.getPageSize());
    assertTrue(pr.getSort().isUnsorted());
  }

  @Test
  void testPageRequest_returnsUnsorted_whenSortIsEmpty() {
    SortedSet<String> sort = new TreeSet<>();
    PageRequest pr = RepoService.pageRequest(sort, true, 1, 10);
    assertNotNull(pr);
    assertEquals(1, pr.getPageNumber());
    assertEquals(10, pr.getPageSize());
    assertTrue(pr.getSort().isUnsorted());
  }

  @Test
  void testPageRequest_returnsSortedAscending_whenOrderAscendingIsTrue() {
    SortedSet<String> sort = new TreeSet<>();
    sort.add("id");
    sort.add("name");

    PageRequest pr = RepoService.pageRequest(sort, true, 2, 20);
    assertNotNull(pr);
    assertEquals(2, pr.getPageNumber());
    assertEquals(20, pr.getPageSize());

    Sort.Order idOrder = pr.getSort().getOrderFor("id");
    Sort.Order nameOrder = pr.getSort().getOrderFor("name");

    assertNotNull(idOrder);
    assertNotNull(nameOrder);
    assertEquals(Direction.ASC, idOrder.getDirection());
    assertEquals(Direction.ASC, nameOrder.getDirection());
  }

  @Test
  void testPageRequest_returnsSortedDescending_whenOrderAscendingIsFalse() {
    SortedSet<String> sort = new TreeSet<>();
    sort.add("id");

    PageRequest pr = RepoService.pageRequest(sort, false, 0, 5);
    assertNotNull(pr);
    assertEquals(0, pr.getPageNumber());
    assertEquals(5, pr.getPageSize());

    Sort.Order idOrder = pr.getSort().getOrderFor("id");
    assertNotNull(idOrder);
    assertEquals(Direction.DESC, idOrder.getDirection());
  }
}
