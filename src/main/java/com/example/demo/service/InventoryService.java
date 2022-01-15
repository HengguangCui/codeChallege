package com.example.demo.service;

import com.example.demo.entity.Inventory;
import com.example.demo.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryMapper inventoryMapper;

    /**
     *
     * @param id
     * @return
     */
    public Inventory getInventoryInfo(int id) {
        return inventoryMapper.getInventoryInfo(id);
    }

    /**
     *
     * @param inventory
     * @return
     */
    public int addInventory(Inventory inventory) {
        return inventoryMapper.addInventory(inventory);
    }

    /**
     *
     * @param id
     * @return
     */
    public int deleteById(int id) {
        return inventoryMapper.deleteById(id);
    }

    /**
     *
     * @param inventory
     * @return
     */
    public int updateInventory(Inventory inventory) {
        return inventoryMapper.updateInventory(inventory);
    }

    /**
     *
     * @return
     */
    public List<Inventory> selectAll() {
        return inventoryMapper.selectAll();
    }

}
