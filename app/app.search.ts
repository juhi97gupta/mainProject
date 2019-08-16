import { Product } from "./app.Product";
import { ProductService } from "./app.ProductServiceClass";
import { Component } from "@angular/core";
import { Observable } from "rxjs";



@Component({
    selector: 'add-comp',
    templateUrl: 'search.html'
})
 export class SearchClass{
     products: Product=new Product()
    products1: Observable<Product[]>;
    constructor(private  service: ProductService){}
  //   mobile: any;
  // account: Account;
  status = false;
  
    
    ngOnInit() {
      this.reloadData();
      }
    
      // private searchCustomers() {
      //   console.log("service")
      //   this.service.getCustomersByMobile(this.mobile)
      //     .subscribe(res => {this.ob = res;
      //     });
    
      // onSubmit() {
      //   this.searchCustomers();
      // }
      
      onUpdate(){
        this.status = true;
      }
    
      deleteCustomer(index:any) {
          this.service.deleteAccount(this.products[index].productID).subscribe( data => {console.log(data)
            
},
          error => console.log(error));
          
        }
    
         updateCustomer(index:any) {
        this.service.updateAccount(this.products.productID,
          { productName:this.products. productName, merchant: this.products.merchant, tag: this.products.tag,company: this.products.company,  description: 
            this.products. description,  photo: this.products. photo,quantity: this.products. quantity, category: this.products.category,  subcategory: this.products. subcategory,
             soldQuantities: this.products.soldQuantities})
          .subscribe( data => {console.log(data)
             
 },
            error => console.log(error));
            this.status = false;
      }
      
         reloadData() {
           this.products1 = this.service.getCustomersList();
           console.log(this.products1)
         }
    }
    