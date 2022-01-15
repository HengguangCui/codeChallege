package com.example.demo.controller;

import com.example.demo.entity.Inventory;
import com.example.demo.service.InventoryService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * home page, initialize the database
     * @return
     */
    @RequestMapping(value = "/index")
    public void index() {

        String createTable = "CREATE Table IF NOT EXISTS inventory \n" +
                "(\n" +
                "\tproduct_id serial PRIMARY KEY,\n" +
                "\tproduct_name VARCHAR(50) UNIQUE NOT NULL,\n" +
                "\tremain_num INT NOT NULL,\n" +
                "\tprice INT NOT NULL CHECK (price > 0)\n" +
                ");";
        jdbc.update(createTable);

    }

    /**
     * select a record by ID
     * @param id
     * @return record Info
     */
    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    public String getInfo(@PathVariable int id){
        return inventoryService.getInventoryInfo(id).toString();
    }

    /**
     * delete a record by ID
     * @param id
     * @return error checking
     */
    @RequestMapping(value = "/delete")
    public String deleteRecord(@RequestParam(value="id") int id) {
        int res = inventoryService.deleteById(id);
        return "/index";
    }

    /**
     * inventory helper
     * @return
     */
    @ModelAttribute(value="inventory")
    public Inventory myInventory() {
        return new Inventory();
    }

    /**
     * insert a record
     * @param model
     * @return
     * http://localhost:8081/insert?id=100&name=testName&amount=100&price=100
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String insertRecord(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "/index";
    }

    /**
     * process insert
     * @param inventory
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String processInsertRecord(@ModelAttribute("inventory") Inventory inventory) {
        int res = inventoryService.addInventory(inventory);
        return "/index";
    }

    /**
     * update record by id
     * @param inventory
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateRecord(Inventory inventory) {
        int res = inventoryService.updateInventory(inventory);
        return "/index";
    }

    /**
     * return all the records
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public List<Inventory> selectAll(Model model) {
        List<Inventory> res = inventoryService.selectAll();
        model.addAttribute("allInventories", res);
        return res;
    }

    /**
     * export data to a csv file
     * @return
     */
    @RequestMapping(value = "/export")
    public String exportRecords() {
        List<Inventory> res = inventoryService.selectAll();
        try {
            File myObj = new File("data.csv");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                CSVWriter writer = new CSVWriter(new FileWriter(myObj));
                // construct string array list
                List<String[]> dataList = new ArrayList<>();
                for(Inventory inventory : res) {
                    String[] recordData = new String[4];
                    recordData[0] = inventory.getId().toString();
                    recordData[1] = inventory.getName();
                    recordData[2] = inventory.getAmount().toString();
                    recordData[3] = inventory.getPrice().toString();
                    dataList.add(recordData);
                }
                // write to csv file
                for (String[] array : dataList) {
                    writer.writeNext(array);
                }
                // close writer
                writer.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "/index";
    }




}
