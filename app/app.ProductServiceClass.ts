import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";



@Injectable({
    providedIn: 'root'
})
export class ProductService{
   

    private baseUrl = 'http://localhost:5000/products';

    constructor(private http: HttpClient){}

    getCustomersList(): Observable<any> {
        return this.http.get(`${this.baseUrl}`  + `/all`);
      }
    
      array: any[] = [];

      createAccount(Product: Object): Observable<Object> {
        console.log("add")
        let object1 = {
          "merchantId":"103"
        }
        this.array.push(object1);
        this.array.push(Product);

        return this.http.post(`${this.baseUrl}` + `/add/`+103, Product);
        
      }
    
      updateAccount(productID: number, value: any): Observable<Object> {
        let object = {
          "productID": value.productID,
          "productName": value.productName,
          "merchant": value.merchant,
          "tag": value.tag,
          "company": value.company,
          "photo": value.photo,
          "description": value.description,
          "quantity": value.quantity,
          "category": value.category,
          "subcategory": value.subcategory,
          "soldQuantities": value.soldQuantities
          

        }
       return this.http.put(`${this.baseUrl}/update/`+`${productID}`, object);
       
      }
      // getCustomersByMobile(mobileno: any): Observable<Account> {
       
      //   return this.http.get<Account>(`${this.baseUrl}/`+`${mobileno}`);
      // }

     deleteAccount(productID: number): Observable<any> {
       console.log(productID)
       return this.http.delete(`${this.baseUrl}/delete/` +` ${productID}`);
     }
     /*
     withdrawMoney(mobile: any,amount:any): Observable<any> {
       ///withdraw/mobile/{mobile}/amount/{amount}
       console.log(`${this.baseUrl}/withdraw/mobile/${mobile}/amount/${amount}`)
      return this.http.get(`${this.baseUrl}/withdraw/mobile/${mobile}/amount/${amount}`);
    }
  
    transferMoney(from: any,to:any,amount:any): Observable<any> {
      ////amount/from/{from}/to/{to}/amount/{amount}
      console.log(`${this.baseUrl}/${from}/${to}/amount/${amount}`)
      return this.http.get(`${this.baseUrl}/${from}/${to}/amount/${amount}`);
    }

    depositeMoney(mobile: any,amount:any): Observable<any> {
    
     return this.http.get(`${this.baseUrl}/deposite/${mobile}/${amount}`);
}*/
}