import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IFile } from '../file.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../file.test-samples';

import { FileService } from './file.service';

const requireRestSample: IFile = {
  ...sampleWithRequiredData,
};

describe('File Service', () => {
  let service: FileService;
  let httpMock: HttpTestingController;
  let expectedResult: IFile | IFile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FileService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a File', () => {
      const file = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(file).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of File', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a File', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addFileToCollectionIfMissing', () => {
      it('should add a File to an empty array', () => {
        const file: IFile = sampleWithRequiredData;
        expectedResult = service.addFileToCollectionIfMissing([], file);
        expect(expectedResult).toEqual([file]);
      });

      it('should not add a File to an array that contains it', () => {
        const file: IFile = sampleWithRequiredData;
        const fileCollection: IFile[] = [
          {
            ...file,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFileToCollectionIfMissing(fileCollection, file);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a File to an array that doesn't contain it", () => {
        const file: IFile = sampleWithRequiredData;
        const fileCollection: IFile[] = [sampleWithPartialData];
        expectedResult = service.addFileToCollectionIfMissing(fileCollection, file);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(file);
      });

      it('should add only unique File to an array', () => {
        const fileArray: IFile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fileCollection: IFile[] = [sampleWithRequiredData];
        expectedResult = service.addFileToCollectionIfMissing(fileCollection, ...fileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const file: IFile = sampleWithRequiredData;
        const file2: IFile = sampleWithPartialData;
        expectedResult = service.addFileToCollectionIfMissing([], file, file2);
        expect(expectedResult).toEqual([file, file2]);
      });

      it('should accept null and undefined values', () => {
        const file: IFile = sampleWithRequiredData;
        expectedResult = service.addFileToCollectionIfMissing([], null, file, undefined);
        expect(expectedResult).toEqual([file]);
      });

      it('should return initial array if no File is added', () => {
        const fileCollection: IFile[] = [sampleWithRequiredData];
        expectedResult = service.addFileToCollectionIfMissing(fileCollection, undefined, null);
        expect(expectedResult).toEqual(fileCollection);
      });
    });

    describe('compareFile', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 'b4cda08e-50b3-4383-a397-04fa73612acd' };
        const entity2 = null;

        const compareResult1 = service.compareFile(entity1, entity2);
        const compareResult2 = service.compareFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 'b4cda08e-50b3-4383-a397-04fa73612acd' };
        const entity2 = { id: '1c765935-8c23-4862-8c32-05427860e396' };

        const compareResult1 = service.compareFile(entity1, entity2);
        const compareResult2 = service.compareFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 'b4cda08e-50b3-4383-a397-04fa73612acd' };
        const entity2 = { id: 'b4cda08e-50b3-4383-a397-04fa73612acd' };

        const compareResult1 = service.compareFile(entity1, entity2);
        const compareResult2 = service.compareFile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
