package com.example.demo.mapper;

import com.example.demo.entity.Inventory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMapper {
    /**
     * Get Inventory Info
     * @param Id
     * @return
     */
    Inventory getInventoryInfo(int Id);

    /**
     * Insert an inventory record
     * @param inventory
     * @return
     */
    int addInventory(Inventory inventory);

    /**
     * Delete a record by ID
     * @param Id
     * @return
     */
    int deleteById(int Id);

    /**
     * Update a record
     * @param inventory
     * @return
     */
    int updateInventory(Inventory inventory);

    /**
     * return all the records
     * @return
     */
    List<Inventory> selectAll();
}
